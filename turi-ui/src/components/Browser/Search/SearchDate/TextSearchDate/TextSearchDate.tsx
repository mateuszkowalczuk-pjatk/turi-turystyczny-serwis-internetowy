import { useTranslation } from "react-i18next";
import TextRegular from "../../../../Text/TextRegular";
import styles from './TextSearchDate.module.css';

const TextSearchDate = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.text}>
            <TextRegular
                text={t('dashboard.calendar-default')}
            />
        </div>
    )
}

export default TextSearchDate;