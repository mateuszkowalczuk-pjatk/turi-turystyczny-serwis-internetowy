import { RootState } from '../../../store/store.ts'
import { useSelector } from 'react-redux'
import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'
import TourismContent from '../../../components/Tourism/TourismContent'
import PageTitle from '../../../components/Shared/PageTitle'
import TourismPanel from '../../../components/Tourism/TourismPanel'
import TourismHeader from '../../../components/Tourism/TourismHeader'
import TourismCurrentReservations from '../../../components/Tourism/TourismCurrentReservations'
import TourismReservations from '../../../components/Tourism/TourismReservations'
import TourismTouristicPlace from '../../../components/Tourism/TourismTouristicPlace'
import { useRedirectSome } from '../../../hooks/useRedirect.ts'

const TourismPage = () => {
    const { t } = useTranslation()
    const navigate = useNavigate()
    const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated)
    const isPremium = useSelector((state: RootState) => state.premium.isPremiumAccount)

    useRedirectSome([!isAuthenticated, !isPremium], '/')

    return (
        <TourismContent
            title={<PageTitle text={t('tourism.title')} />}
            firstPanel={
                <TourismPanel
                    header={
                        <TourismHeader
                            text={t('tourism.current-reservations-title')}
                            secondButtonText={t('tourism.current-reservations-stays-plan')}
                            secondButtonOnClick={() => navigate('/tourism/stays-plan')}
                        />
                    }
                    content={<TourismCurrentReservations />}
                    size={'current'}
                />
            }
            secondPanel={
                <TourismPanel
                    header={
                        <TourismHeader
                            text={t('tourism.upcoming-reservations-title')}
                            secondButtonText={t('tourism.upcoming-reservations-plan')}
                            secondButtonOnClick={() => navigate('/tourism/reservations-plan')}
                        />
                    }
                    content={<TourismReservations />}
                    size={'reservations'}
                />
            }
            thirdPanel={
                <TourismPanel
                    header={<TourismHeader text={t('tourism.touristic-place-title')} />}
                    content={<TourismTouristicPlace />}
                    size={'place'}
                />
            }
            fourthPanel={
                <TourismPanel
                    header={
                        <TourismHeader
                            text={t('tourism.offers-title')}
                            firstButtonText={t('tourism.offers-stay')}
                            firstButtonOnClick={() => navigate('/tourism/stay-offer')}
                            secondButtonText={t('tourism.offers-attraction')}
                            secondButtonOnClick={() => navigate('/tourism/attraction-offer')}
                        />
                    }
                    content={<TourismReservations />}
                    size={'reservations'}
                />
            }
            fifthPanel={
                <TourismPanel
                    header={<TourismHeader text={t('tourism.all-reservations-title')} />}
                    content={<TourismReservations />}
                    size={'reservations'}
                />
            }
        />
    )
}

export default TourismPage
