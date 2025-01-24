import { Trans } from 'react-i18next'
import { useHooks } from '../../../hooks/shared/useHooks.ts'
import Checkbox from '../../Shared/Controls/Checkbox'
import TextMedium from '../../Shared/Controls/Text/TextMedium'
import { PaymentMethod } from '../../../types'
import { Link } from 'react-router-dom'
import styles from './PremiumPayment.module.css'

interface Props {
    paymentMethod: PaymentMethod | null
    setPaymentMethod: (option: PaymentMethod) => void
    privacyPolicy: boolean
    setPrivacyPolicy: (option: boolean) => void
}

const PremiumPayment = ({ paymentMethod, setPaymentMethod, privacyPolicy, setPrivacyPolicy }: Props) => {
    const { t } = useHooks()

    return (
        <div className={styles.payment}>
            <TextMedium text={t('premium.payment-option')} />
            <div className={styles.paymentOptions}>
                <div
                    className={`${styles.paymentOption} ${paymentMethod === PaymentMethod.CARD ? styles.paymentOptionSelected : ''}`}
                    onClick={() => setPaymentMethod(PaymentMethod.CARD)}
                >
                    {t('premium.payment-card')}
                </div>
                <div
                    className={`${styles.paymentOption} ${paymentMethod === PaymentMethod.BLIK ? styles.paymentOptionSelected : ''}`}
                    onClick={() => setPaymentMethod(PaymentMethod.BLIK)}
                >
                    {'BLIK'}
                </div>
            </div>
            <Checkbox
                checked={privacyPolicy}
                onChange={() => setPrivacyPolicy(!privacyPolicy)}
                textPolicy={
                    <Trans
                        i18nKey="premium.payment-privacy-policy"
                        components={{
                            1: (
                                <Link
                                    to="/info/privacy-policy"
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
