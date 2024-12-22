import TourismContent from '../../../components/Tourism/TourismContent'
import PageTitle from '../../../components/Shared/PageTitle'
import TourismPanel from '../../../components/Tourism/TourismPanel'
import TourismHeader from '../../../components/Tourism/TourismHeader'
import TourismCurrentReservations from '../../../components/Tourism/TourismCurrentReservations'

const TourismStaysPlanPage = () => {
    return (
        <TourismContent
            title={<PageTitle text={''} />}
            firstPanel={
                <TourismPanel
                    header={
                        <TourismHeader
                            text={''}
                            secondButtonText={''}
                            secondButtonOnClick={() => console.log('Powrót')}
                        />
                    }
                    content={<TourismCurrentReservations />}
                    size={'offer'}
                />
            }
        />
    )
}

export default TourismStaysPlanPage
