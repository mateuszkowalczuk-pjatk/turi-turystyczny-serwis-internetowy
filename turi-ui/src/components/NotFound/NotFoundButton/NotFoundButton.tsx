import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { GreenButton } from '../../Shared/Controls/Button'
import styles from './NotFoundButton.module.css'

const NotFoundButton = () => {
    const { t, navigate } = useHooks()

    return (
        <div className={styles.button}>
            <GreenButton
                text={t('not-found.button')}
                onClick={() => navigate('/')}
            />
        </div>
    )
}

export default NotFoundButton
