import Map from '../../../Controls/Map'
import styles from './SearchMap.module.css'

const SearchMap = () => {
    function handleOnClick() {
        console.log('Map')
    }

    return (
        <div className={styles.map} onClick={handleOnClick} role="button" tabIndex={0}>
            <Map />
        </div>
    )
}

export default SearchMap