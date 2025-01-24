import { useTouristicPlace } from '../../../hooks/pages/useTouristicPlace.ts'
import { useRedirectSome } from '../../../hooks/shared/useRedirect.ts'
import PageContent from '../../../components/Shared/Contents/PageContent'
import PageTitle from '../../../components/Shared/PageTitle'
import TourismPanel from '../../../components/Tourism/TourismPanel'
import TourismHeader from '../../../components/Tourism/TourismHeader'
import TourismReservations from '../../../components/Tourism/TourismReservations'
import TourismTouristicPlace from '../../../components/Tourism/TourismTouristicPlace'
import TourismOffers from '../../../components/Tourism/TourismOffers'
import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { useStates } from '../../../hooks/shared/useStates.ts'
import { ReservationStatus } from '../../../types/reservation.ts'

const TourismPage = () => {
    const { t, navigate } = useHooks()
    const { isAuthenticated, isPremium } = useStates()
    const { touristicPlaceId } = useTouristicPlace()

    useRedirectSome([!isAuthenticated, !isPremium], '/')

    return (
        <PageContent
            title={<PageTitle text={t('tourism.title')} />}
            firstPanel={
                <TourismPanel
                    header={<TourismHeader text={t('tourism.current-reservations-title')} />}
                    content={
                        touristicPlaceId && (
                            <TourismReservations
                                touristicPlaceId={touristicPlaceId}
                                statuses={[ReservationStatus.REALIZATION]}
                            />
                        )
                    }
                    size={'reservations'}
                />
            }
            secondPanel={
                <TourismPanel
                    header={<TourismHeader text={t('tourism.upcoming-reservations-title')} />}
                    content={
                        touristicPlaceId && (
                            <TourismReservations
                                touristicPlaceId={touristicPlaceId}
                                statuses={[ReservationStatus.RESERVATION]}
                            />
                        )
                    }
                    size={'reservations'}
                />
            }
            thirdPanel={
                <TourismPanel
                    header={<TourismHeader text={t('tourism.touristic-place-title')} />}
                    content={touristicPlaceId && <TourismTouristicPlace touristicPlaceId={touristicPlaceId} />}
                    size={'place'}
                />
            }
            fourthPanel={
                <TourismPanel
                    header={
                        <TourismHeader
                            text={t('tourism.offers-title')}
                            firstButtonText={t('tourism.offers-stay')}
                            firstButtonOnClick={() =>
                                navigate('/tourism/create-stay-offer', {
                                    state: { touristicPlaceId: touristicPlaceId }
                                })
                            }
                            secondButtonText={t('tourism.offers-attraction')}
                            secondButtonOnClick={() =>
                                navigate('/tourism/create-attraction-offer', {
                                    state: { touristicPlaceId: touristicPlaceId }
                                })
                            }
                        />
                    }
                    content={touristicPlaceId && <TourismOffers touristicPlaceId={touristicPlaceId} />}
                    size={'reservations'}
                />
            }
            fifthPanel={
                <TourismPanel
                    header={<TourismHeader text={t('tourism.all-reservations-title')} />}
                    content={
                        touristicPlaceId && (
                            <TourismReservations
                                touristicPlaceId={touristicPlaceId}
                                statuses={[
                                    ReservationStatus.RESERVATION,
                                    ReservationStatus.REALIZATION,
                                    ReservationStatus.REALIZED
                                ]}
                            />
                        )
                    }
                    size={'reservations'}
                />
            }
        />
    )
}

export default TourismPage
