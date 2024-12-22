import styles from './TextRegular.module.css'

interface Props {
    text: string
    className?: string
    onClick?: () => void
}

const TextRegular = ({ text, className, onClick }: Props) => {
    return (
        <p
            className={`${styles.text} ${className}`}
            onClick={onClick}
        >
            {text}
        </p>
    )
}

export default TextRegular
