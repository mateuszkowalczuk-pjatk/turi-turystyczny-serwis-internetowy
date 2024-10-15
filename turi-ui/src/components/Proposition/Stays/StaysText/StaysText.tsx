import { useTranslation } from "react-i18next";
import TextMedium from "../../../Controls/Text/TextMedium";
import styles from './StaysText.module.css'

const StaysText = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.text}>
            <TextMedium
                text={t('proposition.stay')}
            />
        </div>
    )
}

export default StaysText;