import styles from './TextBold.module.css'

const TextBold = ({ text }: { text: string }) => {
    return (
        <p className={styles.text}>
            { text }
        </p>
    )
}

export default TextBold