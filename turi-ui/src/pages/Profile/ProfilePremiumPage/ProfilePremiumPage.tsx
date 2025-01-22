import React, { useEffect, useState } from 'react'
import { bankAccountNumberValidation, nipValidation } from '../../../utils/companyValidation.ts'
import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'
import { usePremium } from '../../../store/slices/premium.ts'
import { useAuth } from '../../../hooks/app/useAuth.ts'
import ProfilePremium from '../../../components/Profile/ProfilePremium'
import ProfileButton from '../../../components/Profile/ProfileButton'
import PersonalPart from '../../../components/Shared/Personal/PersonalPart'
import PersonalPanel from '../../../components/Shared/Personal/PersonalPanel'
import PersonalInput from '../../../components/Shared/Personal/PersonalInput'
import { Premium, PremiumCompanyParam } from '../../../types'
import { premiumService } from '../../../services/premiumService.ts'
import styles from './ProfilePremiumPage.module.css'
import Label from '../../../components/Shared/Controls/Label'

interface FormData {
    companyName: string
    nip: string
    bankAccountNumber: string
}

const ProfilePremiumPage = () => {
    const { t } = useTranslation()
    const navigate = useNavigate()
    const isPremium = usePremium()
    const [error, setError] = useState<string | null>(null)
    const [loading, setLoading] = useState(false)
    const [formData, setFormData] = useState<FormData>({
        companyName: '',
        nip: '',
        bankAccountNumber: ''
    })

    useAuth('/')

    useEffect(() => {
        if (!isPremium) navigate('/')

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
    }, [isPremium, navigate])

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
                        label={<Label text={t('profile.premium-company-name')} />}
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
                        label={<Label text={t('profile.premium-nip')} />}
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
                        label={<Label text={t('profile.premium-bank-account-number')} />}
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
