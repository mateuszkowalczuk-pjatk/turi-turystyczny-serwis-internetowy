import { useTranslation } from 'react-i18next'
import TextExtraLight from '../../../../Text/TextExtraLight'
import TextRegular from '../../../../Text/TextRegular'
import styles from './OfferContent.module.css'

const OfferContent = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.offer}>
            <TextExtraLight
                text={t('footer.offers')}
            />
            <TextRegular
                text={t('footer.stays')}
            />
            <TextRegular
                text={t('footer.attractions')}
            />
            <TextRegular
                text={t('footer.rating')}
            />
        </div>
    )
}

export default OfferContent;