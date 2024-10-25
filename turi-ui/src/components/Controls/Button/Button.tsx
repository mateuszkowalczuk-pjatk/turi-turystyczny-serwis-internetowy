import styles from './Button.module.css'

interface Props {
    text: string
    onClick?: () => void
    className?: string
    type?: 'button' | 'submit' | 'reset'
}

export const GreyButton = ({ text, onClick, className }: Props) => {
    return (
        <button
            className={`${styles.button} ${styles.greyButton} ${className ? className : ''}`}
            onClick={onClick}
        >
            {text}
        </button>
    )
}

export const GreenButton = ({ text, onClick, type }: Props) => {
    return (
        <button
            className={`${styles.button} ${styles.greenButton}`}
            onClick={onClick}
            type={type}
        >
            {text}
        </button>
    )
}
