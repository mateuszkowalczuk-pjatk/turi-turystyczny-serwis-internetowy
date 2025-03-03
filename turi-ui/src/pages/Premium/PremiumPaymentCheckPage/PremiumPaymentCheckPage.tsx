import { useAuth } from '../../../hooks/app/useAuth.ts'
import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { useEffect } from 'react'
import { premiumAccount } from '../../../store/slices/premium.ts'
import { premiumService } from '../../../services/premiumService.ts'
import { touristicPlaceService } from '../../../services/touristicPlaceService.ts'
import { notPaymentPremiumFailed, paymentPremiumFailed } from '../../../store/slices/premiumPaymentFailed.ts'
import Spinner from '../../../components/Shared/Loading/Spinner'
import styles from './PremiumPaymentCheckPage.module.css'

const PremiumPaymentCheckPage = () => {
    const { navigate, dispatch } = useHooks()

    useAuth('/')

    useEffect(() => {
        dispatch(notPaymentPremiumFailed())

        let attempts = 0
        const interval = setInterval(() => {
            const checkPayment = async () => {
                const response = await premiumService.checkPayment()
                if (response.status === 200) {
                    await touristicPlaceService.create()
                    dispatch(premiumAccount())
                    dispatch(notPaymentPremiumFailed())
                    navigate('/premium/summary')
                } else {
                    dispatch(paymentPremiumFailed())
                    navigate('/premium/payment')
                }
            }
            checkPayment().catch((error) => error)

            attempts++
            if (attempts >= 12) {
                clearInterval(interval)
                dispatch(paymentPremiumFailed())
                navigate('/premium/payment')
            }
        }, 5000)

        return () => clearInterval(interval)
    }, [dispatch, navigate])

    return (
        <div className={styles.check}>
            <Spinner />
        </div>
    )
}

export default PremiumPaymentCheckPage
