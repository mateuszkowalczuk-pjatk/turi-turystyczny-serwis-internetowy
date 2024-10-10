import { useTranslation } from "react-i18next";
import TextExtraLight from "../../../Text/TextExtraLight";
import TextRegular from "../../../Text/TextRegular";
import styles from './MoreContent.module.css'

const MoreContent = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.more}>
            <TextExtraLight
                text={t('footer.more')}
            />
            <TextRegular
                text={t('footer.about')}
            />
            <TextRegular
                text={t('footer.conditions')}
            />
            <TextRegular
                text={t('footer.privacy')}
            />
            <TextRegular
                text={t('footer.help')}
            />
        </div>
    )
}

export default MoreContent;