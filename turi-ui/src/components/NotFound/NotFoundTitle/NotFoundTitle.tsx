import { useHooks } from '../../../hooks/shared/useHooks.ts'
import TextMedium from '../../Shared/Controls/Text/TextMedium'
import styles from './NotFoundTitle.module.css'

const NotFoundTitle = () => {
    const { t } = useHooks()

    return (
        <div className={styles.title}>
            <TextMedium text={t('not-found.title')} />
        </div>
    )
}

export default NotFoundTitle
