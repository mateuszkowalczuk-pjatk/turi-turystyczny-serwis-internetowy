import TextBold from '../../../Shared/Controls/Text/TextBold'
import styles from './MainPropositionPanel.module.css'

interface Props {
    text: string
    imagePath: string
}

const MainPropositionPanel = ({ text, imagePath }: Props) => {
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

export default MainPropositionPanel
