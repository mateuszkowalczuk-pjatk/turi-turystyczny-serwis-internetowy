import { RootState } from '../../../store/store.ts'
import { useNavigate } from 'react-router-dom'
import { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import PremiumLoader from '../../../components/Premium/PremiumLoader'
import { useAuth } from '../../../hooks/useAuth.ts'
import { notPaymentPremiumFailed, paymentPremiumFailed } from '../../../store/slices/premiumPaymentFailed.ts'
import { premiumService } from '../../../services/premiumService.ts'
import { premiumAccount } from '../../../store/slices/premium.ts'
import styles from './PremiumPaymentCheckPage.module.css'

const PremiumPaymentCheckPage = () => {
    const navigate = useNavigate()
    const dispatch = useDispatch()
    const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated)
    const [dots, setDots] = useState('')

    useAuth('/')

    useEffect(() => {
        dispatch(notPaymentPremiumFailed())

        let attempts = 0
        const interval = setInterval(() => {
            const checkPayment = async () => {
                const response = await premiumService.checkPayment()
                if (response.status === 200) {
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
    }, [dispatch, isAuthenticated, navigate])

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
