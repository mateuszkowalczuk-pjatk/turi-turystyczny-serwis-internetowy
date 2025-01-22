import React, { useEffect, useState } from 'react'
import { useAppDispatch } from '../../../hooks/app/useAppDispatch.ts'
import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'
import { GreenButton } from '../../../components/Shared/Controls/Button'
import { useAuth } from '../../../hooks/app/useAuth.ts'
import PremiumDescription from '../../../components/Premium/PremiumDescription'
import PremiumSection from '../../../components/Premium/PremiumSection'
import PremiumVerify from '../../../components/Premium/PremiumVerify'
import PremiumButtons from '../../../components/Premium/PremiumButtons'
import PremiumPayment from '../../../components/Premium/PremiumPayment'
import { PremiumOffer, PaymentMethod } from '../../../types'
import {
    notPaymentPremiumFailed,
    paymentPremiumFailed,
    usePremiumPaymentFailed
} from '../../../store/slices/premiumPaymentFailed.ts'
import { notBuyPremium, usePremiumBuy } from '../../../store/slices/premiumBuy.ts'
import { premiumService } from '../../../services/premiumService.ts'
import styles from './PremiumPaymentPage.module.css'

const PremiumPaymentPage = () => {
    const { t } = useTranslation()
    const dispatch = useAppDispatch()
    const navigate = useNavigate()
    const isPremiumBuy = usePremiumBuy()
    const isPremiumPaymentFailed = usePremiumPaymentFailed()
    const [months, setMonths] = useState<number | null>(null)
    const [date, setDate] = useState<string | null>(null)
    const [paymentMethod, setPaymentMethod] = useState<PaymentMethod | null>(null)
    const [privacyPolicy, setPrivacyPolicy] = useState<boolean>(false)
    const [error, setError] = useState<string | null>(null)

    useAuth('/')

    useEffect(() => {
        if (!isPremiumBuy) navigate('/premium')

        if (isPremiumPaymentFailed) setError(t('premium.payment-failed'))

        dispatch(notPaymentPremiumFailed())

        const fetchOffer = async () => {
            const response = await premiumService.getOffer()
            if (response.status === 200) {
                const offer: PremiumOffer = await response.json()
                setMonths(offer.length)
            }
        }

        fetchOffer().catch((error) => error)

        if (months !== null) {
            const start = new Date()
            const end = new Date(start)
            end.setMonth(start.getMonth() + months)
            const startDate = `${start.getDate()}.${start.getMonth() + 1}.${start.getFullYear()}`
            const endDate = `${end.getDate()}.${end.getMonth() + 1}.${end.getFullYear()}`
            const finalDate = `${startDate || ''} - ${endDate || ''}`
            setDate(finalDate)
        }
    }, [isPremiumPaymentFailed, isPremiumBuy, months, navigate, dispatch, t])

    const handlePayment = async (e: React.FormEvent) => {
        e.preventDefault()

        setError(null)

        if (!privacyPolicy) {
            setError(t('premium.payment-privacy-police-required'))
            return
        }

        if (paymentMethod === null) {
            setError(t('premium.payment-method-required'))
            return
        }

        const response = await premiumService.pay(paymentMethod)
        if (response.status === 200) {
            const url = await response.text()
            dispatch(notBuyPremium())
            dispatch(paymentPremiumFailed())
            window.location.href = url
        } else {
            setError(t('premium.payment-error-default'))
        }
    }

    return (
        <>
            <PremiumDescription text={t('premium.payment-description')} />
            <PremiumSection
                leftPanel={
                    <PremiumVerify
                        text={t('premium.payment-title')}
                        firstInputText={t('premium.payment-access', { months: months })}
                        secondInputText={date || ''}
                        secondInputDescription={t('premium.payment-offer-description')}
                    />
                }
                rightPanel={
                    <PremiumPayment
                        paymentMethod={paymentMethod}
                        setPaymentMethod={setPaymentMethod}
                        privacyPolicy={privacyPolicy}
                        setPrivacyPolicy={setPrivacyPolicy}
                    />
                }
            />
            {error && <div className={styles.error}>{error}</div>}
            <form
                onSubmit={handlePayment}
                className={styles.form}
            >
                <PremiumButtons
                    leftButton={
                        <GreenButton
                            text={t('premium.verify-back-button')}
                            onClick={() => navigate('/premium/verify')}
                            type="button"
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

export default PremiumPaymentPage
