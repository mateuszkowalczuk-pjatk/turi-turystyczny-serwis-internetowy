import { useHooks } from '../../../hooks/shared/useHooks.ts'
import TextRegular from '../../Shared/Controls/Text/TextRegular'
import styles from './AuthPersonalDescription.module.css'

const AuthPersonalDescription = () => {
    const { t } = useHooks()

    return (
        <div className={styles.description}>
            <TextRegular text={t('signup-personal.description')} />
        </div>
    )
}

export default AuthPersonalDescription
