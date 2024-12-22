import styles from './TextExtraLight.module.css'

const TextExtraLight = ({ text }: { text: string }) => {
    return <p className={styles.text}>{text}</p>
}

export default TextExtraLight
