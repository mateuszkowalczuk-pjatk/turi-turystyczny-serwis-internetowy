import styles from './Panel.module.css';

interface Props {
    text: string;
    imagePath: string;
}

const ButtonPanel = ({ text, imagePath }: Props) => {
    return (
        <div
            className={styles.panel}
            style={{ backgroundImage: `url(${imagePath})` }}
        >
            { text }
        </div>
    )
}

export default ButtonPanel;