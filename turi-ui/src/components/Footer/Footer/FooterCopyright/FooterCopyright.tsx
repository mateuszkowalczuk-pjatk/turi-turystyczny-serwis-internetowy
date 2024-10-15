import { useTranslation } from 'react-i18next'
import TextRegular from '../../../Controls/Text/TextRegular'
import styles from './FooterCopyright.module.css'

const FooterCopyright = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.copyright}>
            <TextRegular
                text={t('footer.copyright')}
            />
        </div>
    )
}

export default FooterCopyright;