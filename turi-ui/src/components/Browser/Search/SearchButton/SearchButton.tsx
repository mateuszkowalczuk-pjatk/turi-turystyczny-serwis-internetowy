import { useTranslation } from "react-i18next";
import { GreenButton } from "../../../Button";
import styles from './SearchButton.module.css';

const SearchButton = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.button}>
            <GreenButton
                text={t('dashboard.search-button')}
            />
        </div>
    )
}

export default SearchButton;