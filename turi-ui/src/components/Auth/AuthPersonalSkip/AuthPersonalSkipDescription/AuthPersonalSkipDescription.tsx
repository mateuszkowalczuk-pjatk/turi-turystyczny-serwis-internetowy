import { useHooks } from '../../../../hooks/shared/useHooks.ts'
import TextRegular from '../../../Shared/Controls/Text/TextRegular'
import TextExtraLight from '../../../Shared/Controls/Text/TextExtraLight'
import styles from './AuthPersonalSkipDescription.module.css'

const AuthPersonalSkipDescription = () => {
    const { t } = useHooks()

    return (
        <div className={styles.description}>
            <TextExtraLight text={t('signup-personal.skip-description')} />
            <TextRegular text={t('signup-personal.skip-decision')} />
        </div>
    )
}

export default AuthPersonalSkipDescription
