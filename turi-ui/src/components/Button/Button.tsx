import styles from './Button.module.css'

interface Props {
    text: string;
    onClick?: () => void;
}

export const GreyButton = ({ text, onClick }: Props) => {
    return (
        <button
            className={`${styles.button} ${styles.greyButton}`}
            onClick={onClick}
        >
            { text }
        </button>
    )
}

export const GreenButton = ({ text, onClick }: Props) => {
    return (
        <button
          className={`${styles.button} ${styles.greenButton}`}
          onClick={onClick}
        >
            { text }
        </button>
    )
}