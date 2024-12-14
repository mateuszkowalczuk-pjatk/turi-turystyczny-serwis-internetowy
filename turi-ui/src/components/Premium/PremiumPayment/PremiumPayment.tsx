import { useTranslation } from 'react-i18next'
import TextMedium from '../../Controls/Text/TextMedium'
import Checkbox from '../../Controls/Checkbox'
import { PaymentMethod } from '../../../types'
import { Trans } from 'react-i18next'
import { Link } from 'react-router-dom'
import styles from './PremiumPayment.module.css'

interface Props {
    paymentMethod: PaymentMethod | null
    setPaymentMethod: (option: PaymentMethod) => void
    privacyPolicy: boolean
    setPrivacyPolicy: (option: boolean) => void
}

const PremiumPayment = ({ paymentMethod, setPaymentMethod, privacyPolicy, setPrivacyPolicy }: Props) => {
    const { t } = useTranslation()

    const handleOptionClick = (option: PaymentMethod) => {
        setPaymentMethod(option)
    }

    const handlePrivacyPolicyChange = () => {
        setPrivacyPolicy(!privacyPolicy)
    }

    return (
        <div className={styles.payment}>
            <TextMedium text={t('premium.payment-option')} />
            <div className={styles.paymentOptions}>
                <div
                    className={`${styles.paymentOption} ${paymentMethod === PaymentMethod.CARD ? styles.paymentOptionSelected : ''}`}
                    onClick={() => handleOptionClick(PaymentMethod.CARD)}
                >
                    {t('premium.payment-card')}
                </div>
                <div
                    className={`${styles.paymentOption} ${paymentMethod === PaymentMethod.BLIK ? styles.paymentOptionSelected : ''}`}
                    onClick={() => handleOptionClick(PaymentMethod.BLIK)}
                >
                    {'BLIK'}
                </div>
            </div>
            <Checkbox
                checked={privacyPolicy}
                onChange={handlePrivacyPolicyChange}
                textPolicy={
                    <Trans
                        i18nKey="premium.payment-privacy-policy"
                        components={{
                            1: (
                                <Link
                                    to="/privacy-policy"
                                    className={styles.link}
                                />
                            )
                        }}
                    />
                }
            />
        </div>
    )
}

export default PremiumPayment
