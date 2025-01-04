import TourismContent from '../../../components/Tourism/TourismContent'
import PageTitle from '../../../components/Shared/PageTitle'
import FavouriteOffers from '../../../components/Offer/FavouriteOffers'
import styles from '../../Page.module.css'

const FavouritePage = () => {
    return (
        <div className={styles.page}>
            <TourismContent
                title={<PageTitle text={'Twoje ulubione oferty'} />}
                firstPanel={<FavouriteOffers />}
            />
        </div>
    )
}

export default FavouritePage
