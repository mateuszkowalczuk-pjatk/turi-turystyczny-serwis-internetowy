import { useHooks } from '../../../hooks/shared/useHooks.ts'
import styles from './PageReturn.module.css'

const PageReturn = ({ text }: { text: string }) => {
    const { navigate } = useHooks()

    return (
        <div className={styles.return}>
            <span
                className={styles.text}
                onClick={() => navigate(-1)}
                role="link"
            >
                {text}
            </span>
        </div>
    )
}

export default PageReturn
