import styles from './TextMedium.module.css'

interface Props {
    text: string
}

const TextMedium = ({ text }: Props) => {
    return <p className={styles.text}>{text}</p>
}

export default TextMedium
