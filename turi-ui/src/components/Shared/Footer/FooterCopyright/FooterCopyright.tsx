import { useHooks } from '../../../../hooks/shared/useHooks.ts'
import TextRegular from '../../Controls/Text/TextRegular'
import styles from './FooterCopyright.module.css'

const FooterCopyright = () => {
    const { t } = useHooks()

    return (
        <div className={styles.copyright}>
            <TextRegular text={t('footer.copyright')} />
        </div>
    )
}

export default FooterCopyright
