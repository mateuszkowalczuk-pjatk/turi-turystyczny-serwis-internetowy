import { useTranslation } from "react-i18next";
import { GreenButton } from "../../../Controls/Button";
import styles from './SearchButton.module.css';

const SearchButton = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.button}>
            <GreenButton
                text={t('home.dashboard.search-button')}
            />
        </div>
    )
}

export default SearchButton;