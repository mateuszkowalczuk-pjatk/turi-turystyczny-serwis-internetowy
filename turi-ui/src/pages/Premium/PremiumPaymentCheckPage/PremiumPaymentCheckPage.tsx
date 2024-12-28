import { useEffect, useState } from 'react'
import { useAppDispatch } from '../../../hooks/useAppDispatch.ts'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../../../hooks/useAuth.ts'
import PremiumLoader from '../../../components/Premium/PremiumLoader'
import { notPaymentPremiumFailed, paymentPremiumFailed } from '../../../store/slices/premiumPaymentFailed.ts'
import { premiumAccount } from '../../../store/slices/premium.ts'
import { premiumService } from '../../../services/premiumService.ts'
import { touristicPlaceService } from '../../../services/touristicPlaceService.ts'
import styles from './PremiumPaymentCheckPage.module.css'

const PremiumPaymentCheckPage = () => {
    const dispatch = useAppDispatch()
    const navigate = useNavigate()
    const [dots, setDots] = useState('')

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
            <PremiumLoader
                dots={dots}
                setDots={setDots}
            />
        </div>
    )
}

export default PremiumPaymentCheckPage
