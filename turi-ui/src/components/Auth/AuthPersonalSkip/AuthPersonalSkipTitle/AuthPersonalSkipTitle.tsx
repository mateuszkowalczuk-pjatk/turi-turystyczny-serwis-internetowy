import { useHooks } from '../../../../hooks/shared/useHooks.ts'
import TextMediumExtra from '../../../Shared/Controls/Text/TextMediumExtra'
import styles from './AuthPersonalSkipTitle.module.css'

const AuthPersonalSkipTitle = () => {
    const { t } = useHooks()

    return (
        <div className={styles.title}>
            <TextMediumExtra text={t('signup-personal.skip-title')} />
        </div>
    )
}

export default AuthPersonalSkipTitle
