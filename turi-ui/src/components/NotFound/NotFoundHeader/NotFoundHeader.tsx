import { useHooks } from '../../../hooks/shared/useHooks.ts'
import TextMediumExtra from '../../Shared/Controls/Text/TextMediumExtra'
import styles from './NotFoundHeader.module.css'

const NotFoundHeader = () => {
    const { t } = useHooks()

    return (
        <div className={styles.header}>
            <TextMediumExtra text={t('not-found.404')} />
        </div>
    )
}

export default NotFoundHeader
