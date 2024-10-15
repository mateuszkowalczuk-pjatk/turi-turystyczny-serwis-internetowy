import { useTranslation } from 'react-i18next'
import TextExtraLight from '../../../../Controls/Text/TextExtraLight'
import TextRegular from '../../../../Controls/Text/TextRegular'
import styles from './CommunityContent.module.css'

const CommunityContent = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.community}>
            <TextExtraLight
                text={t('footer.community')}
            />
            <TextRegular
                text={t('footer.sign-in')}
            />
            <TextRegular
                text={t('footer.sign-up')}
            />
            <TextRegular
                text={t('footer.premium')}
            />
        </div>
    )
}

export default CommunityContent;