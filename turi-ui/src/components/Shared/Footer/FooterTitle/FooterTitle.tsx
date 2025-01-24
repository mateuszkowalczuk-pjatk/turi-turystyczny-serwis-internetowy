import { useHooks } from '../../../../hooks/shared/useHooks.ts'
import TextExtraLight from '../../Controls/Text/TextExtraLight'
import styles from './FooterTitle.module.css'

const FooterTitle = () => {
    const { t } = useHooks()

    return (
        <div className={styles.title}>
            <TextExtraLight text={t('footer.title')} />
        </div>
    )
}

export default FooterTitle
