import { useTranslation } from 'react-i18next'
import TextExtraLight from '../../Controls/Text/TextExtraLight'
import styles from './FooterTitle.module.css'

const FooterTitle = () => {
    const { t } = useTranslation()

    return (
        <div className={styles.title}>
            <TextExtraLight text={t('footer.title')} />
        </div>
    )
}

export default FooterTitle
