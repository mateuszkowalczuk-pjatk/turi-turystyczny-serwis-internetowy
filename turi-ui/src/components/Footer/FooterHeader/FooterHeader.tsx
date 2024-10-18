import { useTranslation } from 'react-i18next'
import TextExtraLight from '../../Controls/Text/TextExtraLight'
import styles from './FooterHeader.module.css'

const FooterHeader = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.header}>
            <TextExtraLight
                text={t('footer.header')}
            />
        </div>
    )
}

export default FooterHeader;