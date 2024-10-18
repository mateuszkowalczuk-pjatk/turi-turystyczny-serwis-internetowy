import { useTranslation } from "react-i18next";
import TextMediumExtra from "../../Controls/Text/TextMediumExtra";
import TextExtraLight from "../../Controls/Text/TextExtraLight";
import styles from './Banner.module.css';

const Banner = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.banner}>
            <TextMediumExtra
                text={t('home.dashboard.header-text')}
            />
            <TextExtraLight
                text={t('home.dashboard.text')}
            />
        </div>
    )
}

export default Banner;