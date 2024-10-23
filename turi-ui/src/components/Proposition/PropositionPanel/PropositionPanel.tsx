import TextBold from '../../Controls/Text/TextBold'
import styles from './PropositionPanel.module.css'

interface Props {
    text: string
    imagePath: string
}

const ButtonPanel = ({ text, imagePath }: Props) => {
    return (
        <div
            className={styles.panel}
            style={{ backgroundImage: `url(${imagePath})` }}
            role="button"
            tabIndex={0}
        >
            <TextBold text={text} />
        </div>
    )
}

export default ButtonPanel
