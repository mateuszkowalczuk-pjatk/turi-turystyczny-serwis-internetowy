import styles from './HeaderLogo.module.css'
import { useHooks } from '../../../../hooks/shared/useHooks.ts'

const HeaderLogo = () => {
    const { navigate } = useHooks()

    return (
        <p
            className={styles.logo}
            onClick={() => navigate('/')}
        >
            Turi
        </p>
    )
}

export default HeaderLogo
