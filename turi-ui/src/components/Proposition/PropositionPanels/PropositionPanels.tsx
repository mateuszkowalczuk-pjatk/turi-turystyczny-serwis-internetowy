import ButtonPanel from '../PropositionPanel'
import styles from './PropositionPanels.module.css'

interface Props {
    firstText: string;
    firstImage: string;
    secondText: string;
    secondImage: string;
    thirdText: string;
    thirdImage: string;
    fourthText: string;
    fourthImage: string;
    fifthText: string;
    fifthImage: string;
    sixthText: string;
    sixthImage: string;
}

const PropositionPanels = ({ firstText, firstImage, secondText, secondImage, thirdText, thirdImage, fourthText, fourthImage, fifthText, fifthImage, sixthText, sixthImage }: Props) => {
    return (
        <div className={styles.panels}>
            <ButtonPanel
                text={ firstText }
                imagePath={ firstImage }
            />
            <ButtonPanel
                text={ secondText }
                imagePath={ secondImage }
            />
            <ButtonPanel
                text={ thirdText }
                imagePath={ thirdImage }
            />
            <ButtonPanel
                text={ fourthText }
                imagePath={ fourthImage }
            />
            <ButtonPanel
                text={ fifthText }
                imagePath={ fifthImage }
            />
            <ButtonPanel
                text={ sixthText }
                imagePath={ sixthImage }
            />
        </div>
    )
}

export default PropositionPanels