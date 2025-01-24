import TextRegular from '../Controls/Text/TextRegular'
import styles from './Error.module.css'

const Error = ({ error }: { error: string }) => {
    return (
        <div className={styles.error}>
            <TextRegular text={error} />
        </div>
    )
}

export default Error
