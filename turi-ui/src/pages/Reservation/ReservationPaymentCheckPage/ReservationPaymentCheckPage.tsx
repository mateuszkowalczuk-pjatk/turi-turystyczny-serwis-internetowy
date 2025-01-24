import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { useEffect } from 'react'
import PageReturn from '../../../components/Shared/PageReturn'
import PageContent from '../../../components/Shared/Contents/PageContent'
import ReservationPanel from '../../../components/Reservation/ReservationPanel'
import { ReservationMode } from '../../../types/reservation.ts'
import { authService } from '../../../services/authService.ts'
import { reservationService } from '../../../services/reservationService.ts'

const ReservationPaymentCheckPage = () => {
    const { navigate, dispatch } = useHooks()

    useEffect(() => {
        let attempts = 0
        const interval = setInterval(() => {
            const checkPayment = async () => {
                await authService.refresh()
                const response = await reservationService.checkPayment([ReservationMode.INITIAL])
                if (response.status === 200) {
                    navigate('/reservations')
                } else {
                    navigate('/')
                }
            }
            checkPayment().catch((error) => error)

            attempts++
            if (attempts >= 12) {
                clearInterval(interval)
                navigate('/')
            }
        }, 5000)

        return () => clearInterval(interval)
    }, [dispatch, navigate])

    return (
        <PageContent
            title={<PageReturn text={''} />}
            firstPanel={
                <ReservationPanel
                    step={4}
                    spinner
                />
            }
        />
    )
}

export default ReservationPaymentCheckPage
