import { useTranslation } from "react-i18next";
import TextMediumExtra from "../../Text/TextMediumExtra";
import TextExtraLight from "../../Text/TextExtraLight";
import styles from './Banner.module.css';

const Banner = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.banner}>
            <TextMediumExtra
                text={t('dashboard.header-text')}
            />
            <TextExtraLight
                text={t('dashboard.text')}
            />
        </div>
    )
}

export default Banner;