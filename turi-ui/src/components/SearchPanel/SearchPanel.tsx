import {useTranslation} from "react-i18next";
import Calendar from "./Calendar";
import Search from "./Search";
import Map from "./Map";
import SearchButton from "./SearchButton";
import styles from './SearchPanel.module.css';

const SearchPanel = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.search}>
            <Calendar />
            <Search />
            <Map />
            <SearchButton
                text={t('dashboard.search-button')}
            />
        </div>
    )
}

export default SearchPanel;