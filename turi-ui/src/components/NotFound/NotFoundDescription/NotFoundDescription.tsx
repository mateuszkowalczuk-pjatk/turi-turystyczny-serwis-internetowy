import { useHooks } from '../../../hooks/shared/useHooks.ts'
import TextRegular from '../../Shared/Controls/Text/TextRegular'
import styles from './NotFoundDescription.module.css'

const NotFoundDescription = () => {
    const { t } = useHooks()

    return (
        <div className={styles.description}>
            <TextRegular text={t('not-found.description')} />
        </div>
    )
}

export default NotFoundDescription
