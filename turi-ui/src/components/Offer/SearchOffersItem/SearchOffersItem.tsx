import { SearchTouristicPlaces } from '../../../types/search.ts'
import styles from './SearchOffersItem.module.css'

const SearchOffersItem = ({ offer }: { offer: SearchTouristicPlaces }) => {
    return (
        <div className={styles.item}>
            <p> {offer.touristicPlace.touristicPlaceId} </p>
        </div>
    )
}

export default SearchOffersItem
