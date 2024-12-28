import React, { useEffect, useState } from 'react'
import { bankAccountNumberValidation, nipValidation } from '../../../utils/companyValidation.ts'
import { useAppDispatch } from '../../../hooks/useAppDispatch.ts'
import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'
import { GreenButton } from '../../../components/Shared/Controls/Button'
import { useAuth } from '../../../hooks/useAuth.ts'
import PremiumDescription from '../../../components/Premium/PremiumDescription'
import PremiumSection from '../../../components/Premium/PremiumSection'
import PremiumButtons from '../../../components/Premium/PremiumButtons'
import PremiumVerify from '../../../components/Premium/PremiumVerify'
import PremiumPersonalInput from '../../../components/Premium/PremiumPersonalInput'
import PremiumInput from '../../../components/Premium/PremiumInput'
import { Account, Premium, PremiumCompanyParam, PremiumVerifyParam } from '../../../types'
import { buyPremium, notBuyPremium } from '../../../store/slices/premiumBuy.ts'
import { accountService } from '../../../services/accountService.ts'
import { premiumService } from '../../../services/premiumService.ts'
import styles from './PremiumVerifyPage.module.css'

interface FormData {
    firstName: string
    lastName: string
    bankAccountNumber: string
    companyName: string
    nip: string
}

const PremiumVerifyPage = () => {
    const { t } = useTranslation()
    const dispatch = useAppDispatch()
    const navigate = useNavigate()
    const [formData, setFormData] = useState<FormData>({
        firstName: '',
        lastName: '',
        bankAccountNumber: '',
        companyName: '',
        nip: ''
    })
    const [error, setError] = useState<string | null>(null)
    const [loading, setLoading] = useState(false)

    useAuth('/')

    useEffect(() => {
        const fetchData = async () => {
            const isExistsForAccountResponse = await premiumService.isExistsForAccount()
            if (isExistsForAccountResponse.status === 200 && (await isExistsForAccountResponse.json())) {
                const premiumResponse = await premiumService.getByAccount()
                const accountResponse = await accountService.getById()
                if (premiumResponse.status === 200 && accountResponse.status === 200) {
                    const premium: Premium = await premiumResponse.json()
                    const account: Account = await accountResponse.json()
                    setFormData({
                        firstName: account.firstName || '',
                        lastName: account.lastName || '',
                        bankAccountNumber: premium.bankAccountNumber || '',
                        companyName: premium.companyName || '',
                        nip: premium.nip || ''
                    })
                }
            }
        }

        fetchData().catch((error) => error)
    }, [navigate])

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setError(null)
        const { name, value } = e.target
        setFormData({ ...formData, [name]: value })
    }

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault()
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

        let response
        const isExistsForAccountResponse = await premiumService.isExistsForAccount()
        if (isExistsForAccountResponse.status === 200 && (await isExistsForAccountResponse.json())) {
            const currentAccount = await accountService.getById().then((response) => response.json())
            const account: Account = {
                userId: currentAccount.userId,
                addressId: currentAccount.addressId,
                accountType: currentAccount.accountType,
                activationCode: currentAccount.activationCode,
                activationCodeExpiresAt: currentAccount.activationCodeExpiresAt,
                firstName: formData.firstName.trim(),
                lastName: formData.lastName.trim(),
                birthDate: currentAccount.birthDate,
                phoneNumber: currentAccount.phoneNumber,
                gender: currentAccount.gender
            }
            await accountService.update(account)
            const premiumCompanyParam: PremiumCompanyParam = {
                bankAccountNumber: formData.bankAccountNumber,
                companyName: formData.companyName.trim(),
                nip: formData.nip
            }
            response = await premiumService.updateCompanyDetails(premiumCompanyParam)
        } else {
            const premiumVerifyParam: PremiumVerifyParam = {
                firstName: formData.firstName.trim(),
                lastName: formData.lastName.trim(),
                bankAccountNumber: formData.bankAccountNumber,
                companyName: formData.companyName.trim(),
                nip: formData.nip
            }
            response = await premiumService.verify(premiumVerifyParam)
        }

        if (response.status === 200) {
            dispatch(buyPremium())
            navigate('/premium/payment')
        } else if (response.status === 400) {
            setError(t('premium.verify-invalid-company'))
            setLoading(false)
            dispatch(notBuyPremium())
        } else {
            setError(t('premium.verify-error-default'))
            setLoading(false)
            dispatch(notBuyPremium())
        }
    }

    return (
        <>
            <PremiumDescription text={t('premium.verify-description')} />
            <form
                onSubmit={handleLogin}
                className={styles.form}
            >
                <PremiumSection
                    leftPanel={
                        <PremiumVerify
                            text={t('premium.verify-personal-title')}
                            firstInputText={t('premium.verify-personal-name')}
                            firstInput={
                                <PremiumPersonalInput
                                    firstType={'text'}
                                    firstName={'firstName'}
                                    firstPlaceholder={t('premium.verify-personal-first-name-placeholder')}
                                    firstValue={formData.firstName}
                                    firstOnChange={handleChange}
                                    firstMinLength={3}
                                    firstMaxLength={50}
                                    firstRequired={true}
                                    firstDisabled={loading}
                                    secondType={'text'}
                                    secondName={'lastName'}
                                    secondPlaceholder={t('premium.verify-personal-last-name-placeholder')}
                                    secondValue={formData.lastName}
                                    secondOnChange={handleChange}
                                    secondMinLength={3}
                                    secondMaxLength={50}
                                    secondRequired={true}
                                    secondDisabled={loading}
                                />
                            }
                            secondInputText={t('premium.verify-personal-bank')}
                            secondInputDescription={t('premium.verify-personal-bank-description')}
                            secondInput={
                                <PremiumInput
                                    type={'text'}
                                    name={'bankAccountNumber'}
                                    placeholder={t('premium.verify-personal-bank-placeholder')}
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
                    rightPanel={
                        <PremiumVerify
                            text={t('premium.verify-company-title')}
                            firstInputText={t('premium.verify-company-name')}
                            firstInput={
                                <PremiumInput
                                    type={'text'}
                                    name={'companyName'}
                                    placeholder={t('premium.verify-company-name-placeholder')}
                                    value={formData.companyName}
                                    onChange={handleChange}
                                    minLength={3}
                                    maxLength={50}
                                    required={true}
                                    disabled={loading}
                                />
                            }
                            secondInputText={t('premium.verify-company-nip')}
                            secondInput={
                                <PremiumInput
                                    type={'text'}
                                    name={'nip'}
                                    placeholder={t('premium.verify-company-nip-placeholder')}
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
                />
                {error && <div className={styles.error}>{error}</div>}
                <PremiumButtons
                    leftButton={
                        <GreenButton
                            text={t('premium.verify-back-button')}
                            onClick={() => navigate('/premium')}
                        />
                    }
                    rightButton={
                        <GreenButton
                            text={t('premium.verify-next-button')}
                            type="submit"
                        />
                    }
                />
            </form>
        </>
    )
}

export default PremiumVerifyPage
