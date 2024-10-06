import styles from './Search.module.css';
import {useTranslation} from "react-i18next";

const Search = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.search}>
            <form className={styles.form}>
                <input
                    type="text"
                    placeholder={t('dashboard.placeholder')}
                />
            </form>
        </div>
    )
}

export default Search;