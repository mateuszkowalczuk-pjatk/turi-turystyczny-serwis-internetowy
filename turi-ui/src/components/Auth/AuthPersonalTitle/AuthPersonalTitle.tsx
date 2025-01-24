import { useHooks } from '../../../hooks/shared/useHooks.ts'
import TextMediumExtra from '../../Shared/Controls/Text/TextMediumExtra'
import styles from './AuthPersonalTitle.module.css'

const AuthPersonalTitle = () => {
    const { t } = useHooks()

    return (
        <div className={styles.title}>
            <TextMediumExtra text={t('signup-personal.title')} />
        </div>
    )
}

export default AuthPersonalTitle
