import { useTranslation } from 'react-i18next'
import TextExtraLight from '../../../../Controls/Text/TextExtraLight'
import TextRegular from '../../../../Controls/Text/TextRegular'
import styles from './ContractContent.module.css'

const ContactContent = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.contact}>
            <TextExtraLight
                text={t('footer.contact')}
            />
            <TextRegular
                text={t('footer.email')}
            />
            <TextRegular
                text={t('footer.phone-number')}
            />
        </div>
    )
}

export default ContactContent;