import styles from './SearchTypeButtons.module.css';
import Button from "../Button";
import {useTranslation} from "react-i18next";

const SearchTypeButtons = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.buttons}>
            <Button
                text={t('dashboard.all-button')}
            />
            <Button
                text={t('dashboard.stay-button')}
            />
            <Button
                text={t('dashboard.atraction-button')}
            />
        </div>
    )
}

export default SearchTypeButtons;