import React, { useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'
import { useSelector } from 'react-redux'
import { RootState } from '../../../store/store.ts'
import { useAuth } from '../../../hooks/useAuth.ts'
import { premiumService } from '../../../services/premiumService.ts'
import { Premium, PremiumCompanyParam } from '../../../types'
import ProfilePremium from '../../../components/Profile/ProfilePremium'
import ProfileButton from '../../../components/Profile/ProfileButton'
import { bankAccountNumberValidation, nipValidation } from '../../../utils/companyValidation.ts'
import PersonalPart from '../../../components/Shared/Personal/PersonalPart'
import PersonalPanel from '../../../components/Shared/Personal/PersonalPanel'
import PersonalLabel from '../../../components/Shared/Personal/PersonalLabel'
import PersonalInput from '../../../components/Shared/Personal/PersonalInput'
import styles from './ProfilePremiumPage.module.css'

interface FormData {
    companyName: string
    nip: string
    bankAccountNumber: string
}

const ProfilePremiumPage = () => {
    const { t } = useTranslation()
    const navigate = useNavigate()
    const isPremiumAccount = useSelector((state: RootState) => state.premium.isPremiumAccount)
    const [formData, setFormData] = useState<FormData>({
        companyName: '',
        nip: '',
        bankAccountNumber: ''
    })
    const [error, setError] = useState<string | null>(null)
    const [loading, setLoading] = useState(false)

    useAuth('/')

    useEffect(() => {
        if (!isPremiumAccount) {
            navigate('/')
        }
        const fetchData = async () => {
            const premiumResponse = await premiumService.getByAccount()
            if (premiumResponse.status === 200) {
                const premium: Premium = await premiumResponse.json()
                setFormData({
                    bankAccountNumber: premium.bankAccountNumber,
                    companyName: premium.companyName,
                    nip: premium.nip
                })
            }
        }
        fetchData().catch((error) => error)
    }, [isPremiumAccount, navigate])

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setError(null)
        const { name, value } = e.target
        setFormData({ ...formData, [name]: value })
    }

    const handleCompanyDetailsUpdate = async () => {
        setLoading(true)

        setError(null)

        if (bankAccountNumberValidation(formData.bankAccountNumber)) {
            setError(t('premium.error-invalid-bank-account'))
            return
        }

        if (nipValidation(formData.nip)) {
            setError(t('premium.error-invalid-nip'))
            return
        }
        const premiumCompanyParam: PremiumCompanyParam = {
            bankAccountNumber: formData.bankAccountNumber,
            companyName: formData.companyName.trim(),
            nip: formData.nip
        }
        const response = await premiumService.updateCompanyDetails(premiumCompanyParam)
        if (response.status === 200) {
            setLoading(false)
        } else if (response.status === 400) {
            setError(t('premium.verify-invalid-company'))
            setLoading(false)
        } else {
            setError(t('premium.verify-error-default'))
            setLoading(false)
        }
    }

    return (
        <div className={styles.page}>
            <PersonalPart
                firstPanel={
                    <PersonalPanel
                        label={<PersonalLabel text={t('profile.premium-company-name')} />}
                        firstInput={
                            <PersonalInput
                                type={'text'}
                                name={'companyName'}
                                placeholder={t('profile.premium-company-name-placeholder')}
                                value={formData.companyName}
                                onChange={handleChange}
                                minLength={3}
                                maxLength={50}
                                required={true}
                                disabled={loading}
                            />
                        }
                    />
                }
                secondPanel={
                    <PersonalPanel
                        label={<PersonalLabel text={t('profile.premium-nip')} />}
                        firstInput={
                            <PersonalInput
                                type={'text'}
                                name={'nip'}
                                placeholder={t('profile.premium-nip-placeholder')}
                                value={formData.nip}
                                onChange={handleChange}
                                minLength={10}
                                maxLength={10}
                                required={true}
                                disabled={loading}
                            />
                        }
                    />
                }
                option={
                    <PersonalPanel
                        label={<PersonalLabel text={t('profile.premium-bank-account-number')} />}
                        firstInput={
                            <PersonalInput
                                type={'text'}
                                name={'bankAccountNumber'}
                                placeholder={t('profile.premium-bank-account-number-placeholder')}
                                value={formData.bankAccountNumber}
                                onChange={handleChange}
                                minLength={26}
                                maxLength={26}
                                required={true}
                                disabled={loading}
                            />
                        }
                    />
                }
                button={
                    <ProfileButton
                        handleSave={handleCompanyDetailsUpdate}
                        error={error}
                    />
                }
                isPremium
            />
            <ProfilePremium />
        </div>
    )
}

export default ProfilePremiumPage
