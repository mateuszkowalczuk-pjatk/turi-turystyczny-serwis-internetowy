import MainPropositionPanel from '../MainPropositionPanel'
import styles from './MainPropositionPanels.module.css'

interface Props {
    firstText: string
    firstImage: string
    secondText: string
    secondImage: string
    thirdText: string
    thirdImage: string
    fourthText: string
    fourthImage: string
    fifthText: string
    fifthImage: string
    sixthText: string
    sixthImage: string
}

const MainPropositionPanels = ({
    firstText,
    firstImage,
    secondText,
    secondImage,
    thirdText,
    thirdImage,
    fourthText,
    fourthImage,
    fifthText,
    fifthImage,
    sixthText,
    sixthImage
}: Props) => {
    return (
        <div className={styles.panels}>
            <MainPropositionPanel
                text={firstText}
                imagePath={firstImage}
            />
            <MainPropositionPanel
                text={secondText}
                imagePath={secondImage}
            />
            <MainPropositionPanel
                text={thirdText}
                imagePath={thirdImage}
            />
            <MainPropositionPanel
                text={fourthText}
                imagePath={fourthImage}
            />
            <MainPropositionPanel
                text={fifthText}
                imagePath={fifthImage}
            />
            <MainPropositionPanel
                text={sixthText}
                imagePath={sixthImage}
            />
        </div>
    )
}

export default MainPropositionPanels
