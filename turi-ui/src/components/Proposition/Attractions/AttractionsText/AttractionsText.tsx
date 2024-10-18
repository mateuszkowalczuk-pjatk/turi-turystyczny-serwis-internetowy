import { useTranslation } from "react-i18next";
import TextMedium from "../../../Controls/Text/TextMedium";
import styles from './AttractionsText.module.css'

const AttractionsText = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.text}>
            <TextMedium
                text={t('home.proposition.attraction')}
            />
        </div>
    )
}

export default AttractionsText;